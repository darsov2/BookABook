import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {StoreDetails} from "../StoreDetails";
import {LoansPerDays} from "../LoansPerDays";

@Injectable({
  providedIn: 'root'
})
export class StatsService {

  constructor(private http: HttpClient) {
  }

  getAuthToken() {
    return {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      })
    }
  }

  getInventoryForStore() {
    return this.http.get<number>('http://localhost:8080/api/stats/inventory', this.getAuthToken());
  }

  getLoansPerDays() {
    return this.http.get<LoansPerDays>(`http://localhost:8080/api/stats/loansPerDays`, this.getAuthToken());
  }
}
