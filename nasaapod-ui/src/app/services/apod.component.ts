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
  isZoomed = false;

  constructor(private http: HttpClient) {}

  loadApod() {
    if (!this.selectedDate) return;

    const apiKey = 'UNJuHm4kjwmt56mFn0M2NChggklhgwaQ2owl6rlE';
    const url = `https://api.nasa.gov/planetary/apod?api_key=${apiKey}&date=${this.selectedDate}`;
    
    this.http.get(url).subscribe(data => {
      this.apod = data;
      this.isZoomed = false;
    });
  }

  toggleZoom() {
    this.isZoomed = !this.isZoomed;
  }
}
