import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";
import {catchError, Observable, Subject, throwError} from "rxjs";
import {Book} from "../Book";
import {Category} from "../Category";
import {Author} from "../Author";
import {Publisher} from "../Publisher";
import {BookCard} from "../Book-Card";

@Injectable({
  providedIn: 'root'
})
export class AuthorService {
  constructor(private http: HttpClient) {
    this.refreshEvent.subscribe(() => this.getAuthors())
  }
  public refreshEvent = new Subject<void>();

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json',
      Authorization: 'my-auth-token'
    })
  };

  private handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      console.error('An error occurred:', error.error);
    } else {
      console.error(
        `Backend returned code ${error.status}, body was: `, error.error);
    }
    return throwError(() => new Error('Something bad happened; please try again later.'));
  }

  getAuthors() {
    return this.http.get<Author[]>('http://localhost:8080/api/authors');
  }

  addAuthor(author: Author): Observable<Author> {
    return this.http.post<Author>('http://localhost:8080/api/authors/add', author)
      .pipe(
        catchError(this.handleError)
      );
  }


  deleteAuthor(id: number): Observable<Book> {
    return this.http.delete<Book>(`http://localhost:8080/api/authors/delete/${id}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  getPopularAuthors() {
    return this.http.get<Author[]>('http://localhost:8080/api/authors/popular');
  }
}
