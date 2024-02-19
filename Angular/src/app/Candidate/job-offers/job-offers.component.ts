import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PaginatedJobOfferResponse } from 'src/app/types/job-offer.interface';
import { JobOfferService } from 'src/app/services/job-offer.service';

@Component({
  selector: 'app-job-offers',
  templateUrl: './job-offers.component.html',
  styleUrls: ['./job-offers.component.scss']
})
export class JobOffersComponent implements OnInit {
  jobOffers: PaginatedJobOfferResponse  | null = null;
  currentPage = 0;
  pageSize = 4;
  searchQuery: string = ''; 
  constructor(private router: Router, private jobOfferService: JobOfferService) { }

  ngOnInit(): void {
    this.fetchJobOffers();
  }
  prevPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.fetchJobOffers();
    }
  }
  
  nextPage() {
    if (this.jobOffers && !this.jobOffers.last) { 
      this.currentPage++;
      this.fetchJobOffers();
    }
  }
   

fetchJobOffers() {
  this.jobOfferService.getJobOffers(this.currentPage, this.pageSize,this.searchQuery).subscribe(
    jobOffers => {
      this.jobOffers = jobOffers;
    },
    error => {
      console.error('Error fetching job offers:', error);
    }
  );
}
onSearchChange() {
  this.fetchJobOffers(); 
}

  navigateToJobOffer(jobOfferId: string) {
    this.router.navigate(['/job-offer', jobOfferId]);
  }
}
