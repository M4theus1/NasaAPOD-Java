import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApodService, Apod } from './services/apod';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'nasaapod-ui';
  apod?: Apod;
  selectedDate: string = '';

  constructor(private apodService: ApodService) {}

  ngOnInit() {
    // Load today's APOD by default
    this.loadApod();
  }

  loadApod() {
    this.apodService.getApod(this.selectedDate).subscribe({
      next: (data) => (this.apod = data),
      error: (err) => console.error('❌ Error loading APOD:', err)
    });
  }
}
