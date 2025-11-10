import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NasaService } from './nasa.service';
import { SafeUrlPipe } from './safe-url.pipe'; // ✅ novo import

@Component({
  selector: 'app-apod',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule, SafeUrlPipe], // ✅ adicionado aqui
  templateUrl: './apod.html',
  styleUrls: ['./apod.css']
})
export class ApodComponent implements OnInit {
  title = 'NASA Astronomy Picture of the Day';
  apod: any;
  selectedDate: string = '';

  constructor(private nasaService: NasaService) {}

  ngOnInit() {
    this.loadApod();
  }

  loadApod() {
    this.nasaService.getApod(this.selectedDate).subscribe({
      next: (data) => (this.apod = data),
      error: (err) => console.error('❌ Erro ao carregar APOD:', err)
    });
  }
}
