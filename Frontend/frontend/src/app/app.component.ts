import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {ConfigurationComponent} from './configuration/configuration.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, ConfigurationComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'frontend';
}
