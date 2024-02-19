import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { JobOfferRequest, JobOfferResponse } from '../types/job-offer.interface';
import { Observable } from 'rxjs';
import { BACKEND_URL } from '../utils/backend.url';

@Injectable({
  providedIn: 'root'
})
export class JobOfferService {
  
  constructor(private http: HttpClient) { }

  getRecruiterJobOffers(currentPage: number, pageSize: number, searchQuery: string,username:string|null) {
    let params = new HttpParams()
    .set('page', currentPage.toString())
    .set('size', pageSize.toString());

  if (searchQuery) {
    params = params.set('searchQuery', searchQuery);
  }
  return this.http.get<any>(`${BACKEND_URL}/joboffers/recruiter/${username}`, { params });
  }
  createJobOffer(jobOfferData: JobOfferRequest) {
    return this.http.post<any>(`${BACKEND_URL}/joboffers`, jobOfferData);
  }
  deleteJobOffer(jobOfferId: string) {
    return this.http.delete<any>(`${BACKEND_URL}/joboffers/${jobOfferId}`);
  }
  editJobOffer(jobOfferId: string,jobOffer:JobOfferRequest) {
    return this.http.put<any>(`${BACKEND_URL}/joboffers/${jobOfferId}`,jobOffer);
  }
  getAppliedJobOffers(page: number, size: number,username:string|null, searchQuery?: string): Observable<any> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    if (searchQuery) {
      params = params.set('searchQuery', searchQuery);
    }

    return this.http.get<any>(`${BACKEND_URL}/joboffers/candidate/${username}`, { params });
  }
  getJobOffers(page: number, size: number, searchQuery?: string): Observable<any> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    if (searchQuery) {
      params = params.set('searchQuery', searchQuery);
    }

    return this.http.get<any>(`${BACKEND_URL}/joboffers`, { params });
  }
  getJobOfferById(jobOfferId: string): Observable<JobOfferResponse> {
    return this.http.get<JobOfferResponse>(`${BACKEND_URL}/joboffers/${jobOfferId}`);
  }
  applyToJobOffer(id: string): Observable<any> {
    return this.http.put<any>(`${BACKEND_URL}/candidates/apply/${id}`, null);
  }
}
