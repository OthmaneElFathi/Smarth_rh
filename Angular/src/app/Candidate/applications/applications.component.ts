import { Component, OnInit } from '@angular/core';
import { JobOfferService } from 'src/app/services/job-offer.service';
import { PaginatedJobOfferResponse } from 'src/app/types/job-offer.interface';
import { CandidateService } from 'src/app/services/candidate.service';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-applications',
  templateUrl: './applications.component.html',
  styleUrls: ['./applications.component.scss']
})
export class ApplicationsComponent implements OnInit {
  jobOffers: PaginatedJobOfferResponse | null = null;
  currentPage = 0;
  pageSize = 4;
  searchQuery: string = ''; 
  

  constructor(private jobOfferService: JobOfferService,private router:Router, private candidateService: CandidateService,private authService: AuthService) { }

  ngOnInit(): void {
    this.fetchAppliedJobs();
  }

  fetchAppliedJobs(): void {
    this.jobOfferService.getAppliedJobOffers(this.currentPage,this.pageSize,this.authService.getUsername(),this.searchQuery).subscribe(
      (data: PaginatedJobOfferResponse) => {
        this.jobOffers = data;
      },
      error => {
        console.error('Error fetching applied job offers:', error);
        
      }
    );
  }

  cancelApplication(applicationId: string): void {
    this.candidateService.removeJobApplication(applicationId).subscribe(
      () => {
        
        this.fetchAppliedJobs();
      },
      error => {
        console.error('Error cancelling application:', error);
        
      }
    );
  }
  prevPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.fetchAppliedJobs();
    }
  }
  
  nextPage() {
    if (this.jobOffers && !this.jobOffers.last) { 
      this.currentPage++;
      this.fetchAppliedJobs();
    }
  }
  onSearchChange() {
    this.fetchAppliedJobs(); 
  }
  
    navigateToJobOffer(jobOfferId: string) {
      this.router.navigate(['/applied-job-offer', jobOfferId]);
    }
}
