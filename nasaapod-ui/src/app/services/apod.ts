import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Apod {
  title: string;
  date: string;
  explanation: string;
  url: string;
}

@Injectable({
  providedIn: 'root'
})
export class ApodService {
  private apiUrl = 'http://localhost:8080/api/apod';

  constructor(private http: HttpClient) {}

  getApod(date?: string): Observable<Apod> {
    let params = new HttpParams();
    if (date) {
      params = params.set('date', date);
    }
    return this.http.get<Apod>(this.apiUrl, { params });
  }
}
