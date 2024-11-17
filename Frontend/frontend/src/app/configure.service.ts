import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ConfigureService {

  constructor(private http: HttpClient) { }
  setConfigurations(eventConfiguration: any): Observable<any>{
    return this.http.post("http://localhost:8080/addConfiguration", eventConfiguration)

  }
}
