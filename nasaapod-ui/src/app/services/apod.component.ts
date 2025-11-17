import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-apod',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './apod.component.html',
  styleUrls: ['./apod.component.css']
})
export class ApodComponent {

  selectedDate: string = '';
  apod: any = null;
  errorMessage: string | null = null;
  isZoomed = false;

  today: string = new Date().toISOString().split('T')[0];

  constructor(private http: HttpClient) {}

  loadApod() {
    if (!this.selectedDate) return;

    this.http.get(`http://localhost:8080/api/apod?date=${this.selectedDate}`)
      .subscribe({
        next: (data: any) => {
          this.apod = data;
          this.errorMessage = null;
          this.isZoomed = false;
        },
        error: (err) => {
          this.apod = null;

          if (err.error?.error) {
            this.errorMessage = err.error.error;
          } else if (err.error?.message) {
            this.errorMessage = err.error.message;
          } else {
            this.errorMessage = "Erro inesperado.";
          }
        }
      });
  }

  toggleZoom() {
    this.isZoomed = !this.isZoomed;
  }
}
