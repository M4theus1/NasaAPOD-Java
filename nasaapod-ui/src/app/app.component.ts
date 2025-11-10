import { Component } from '@angular/core';
import { ApodComponent } from './services/apod.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [ApodComponent], // importa o componente principal da NASA
  template: `
    <main class="app-container">
      <app-apod></app-apod>
    </main>
  `,
  styles: [`
    .app-container {
      display: flex;
      justify-content: center;
      align-items: flex-start;
      min-height: 100vh;
      background-color: #000;
      color: #fff;
      font-family: 'Poppins', sans-serif;
      padding: 2rem;
    }
  `]
})
export class AppComponent {}
