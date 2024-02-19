import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PaginatedCandidateResponse } from '../types/candidate.interface';
import { BACKEND_URL } from '../utils/backend.url';

@Injectable({
  providedIn: 'root'
})
export class CandidateService {
  constructor(private http: HttpClient) { }
fetchApplicants(id : string )
{
  return this.http.get<PaginatedCandidateResponse>(`${BACKEND_URL}/candidates/joboffers/${id}`);
}
  checkResumeUploaded(username: string) {
    return this.http.get<boolean>(`${BACKEND_URL}/candidates/resume/${username}`);
  }
  removeJobApplication(id: string|null): Observable<any> {
    return this.http.delete<any>(`${BACKEND_URL}/candidates/remove-application/${id}`);
  }
  downloadProfilePicture(username: string)
  {
    return this.http.get(`${BACKEND_URL}/candidates/picture/${username}`, { responseType: 'blob' });
  }
}
