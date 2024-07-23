import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {StoreDetails} from "../StoreDetails";
import * as http from "node:http";

@Injectable({
  providedIn: 'root'
})
export class LibraryService {

  constructor(private http: HttpClient) { }

  getPopularStores(): Observable<StoreDetails[]> {
    return this.http.get<StoreDetails[]>('http://localhost:8080/api/libraries/stores/popular');
  }
}
