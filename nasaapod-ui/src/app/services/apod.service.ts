import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class ApodService {
    private apiUrl = `${environment.apiUrl}/api/apod`;

    constructor(private http: HttpClient) {}

    getApodData(): Observable<any> {
        return this.http.get(this.apiUrl);
    }
}
