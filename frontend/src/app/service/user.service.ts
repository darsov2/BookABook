import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import * as http from "node:http";
import {UserAvatar} from "../../UserAvatar";
import {LoginResponse} from "../LoginResponse";
import {UserResponse} from "../UserResponse";
import {BehaviorSubject, switchMap, tap} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  public loggedInUser = new BehaviorSubject<UserResponse | undefined>(undefined);
  public loggedInUser$ = this.loggedInUser.asObservable();
  public token: string | undefined;

  constructor(private http: HttpClient) {
  }

  getAuthToken() {
    return {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      })
    }
  }

  getCustomerShortInfoById(id: number) {
    return this.http.get<UserAvatar>('http://localhost:8080/api/users/customer/' + id);
  }

  login(loginRequest: object) {
    return this.http.post<LoginResponse>('http://localhost:8080/auth/login', loginRequest).pipe(
      tap(response => {
        if (response && response.accessToken) {
          localStorage.setItem('token', response.accessToken);
          this.token = response.accessToken;
        }
      }),
      switchMap(() => this.getPrincipal())
    );
  }

  getPrincipal() {
    return this.http.get<UserResponse>('http://localhost:8080/api/users/me', this.getAuthToken()).pipe(
      tap(response => {
        this.loggedInUser.next(response)
        console.log(this.loggedInUser)
      })
    );
  }

  register(registerRequest: object) {
    return this.http.post('http://localhost:8080/auth/register', registerRequest);
  }

  logout() {
    localStorage.removeItem('token')
  }
}
