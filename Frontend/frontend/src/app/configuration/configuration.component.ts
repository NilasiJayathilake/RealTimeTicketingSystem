import { Component } from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, Validators} from '@angular/forms';
import {CommonModule, JsonPipe} from '@angular/common';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {ConfigureService} from '../configure.service';
import {config} from 'rxjs';


@Component({
  selector: 'app-configuration',
  standalone: true,
  imports: [
    FormsModule,
    JsonPipe,
    CommonModule,
    HttpClientModule
  ],
  templateUrl: './configuration.component.html',
  styleUrl: './configuration.component.css'
})
export class ConfigurationComponent {
  constructor(private http: HttpClient) {

  }
  onSubmit(form: any){
    if(form.valid){
      this.http.post("http://localhost:8080/addConfiguration", form.value).subscribe(
        response=>{
          console.log("Configuration Saved Sucessfully", response);
        }

      );

  }
  }
}
