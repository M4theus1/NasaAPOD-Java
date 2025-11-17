import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NasaService {
  private readonly apiKey = '1yZHqn1CzXNRqISe7fUB9x7pCH9cBGiofazxAUqZ'; // 🔑 substitua pela sua se tiver
  private readonly baseUrl = 'https://api.nasa.gov/planetary/apod';

  constructor(private http: HttpClient) {}

  getApod(date?: string): Observable<any> {
    let url = `${this.baseUrl}?api_key=${this.apiKey}`;
    if (date) url += `&date=${date}`;
    return this.http.get(url);
  }
}
