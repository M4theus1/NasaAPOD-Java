import { Component } from '@angular/core';
import { ApodComponent } from './services/apod.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [ApodComponent],
  template: `
    <main class="app-container">
      <app-apod></app-apod>
    </main>
  `,
  styles: [`
    .app-container {
      width: 100%;
      min-height: 100vh;
      margin: 0;
      padding: 0;
      background: none !important;
    }
  `]
})
export class AppComponent {}
