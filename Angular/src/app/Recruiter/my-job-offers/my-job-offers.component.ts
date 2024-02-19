import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { PaginatedJobOfferResponse } from '../../types/job-offer.interface';
import { JobOfferService } from '../../services/job-offer.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-my-job-offers',
  templateUrl: './my-job-offers.component.html',
  styleUrls: ['./my-job-offers.component.scss']
})
export class MyJobOffersComponent implements OnInit {

  jobOffers: PaginatedJobOfferResponse | null = null;
  currentPage = 0;
  pageSize = 4;
  searchQuery: string = ''; 

  constructor(private dialog: MatDialog,private router: Router, private jobOfferService: JobOfferService,private authService: AuthService) { }

  ngOnInit(): void {
    this.fetchRecruiterJobOffers();
  }
  editJobOffer(jobOfferId: string) {
    this.router.navigate(['/edit-job-offer', jobOfferId]);
  }
  
  confirmDelete(jobOfferId: string) {
        this.deleteJobOffer(jobOfferId);
  }
  
  deleteJobOffer(jobOfferId: string) {
    this.jobOfferService.deleteJobOffer(jobOfferId).subscribe(
      () => {
        this.fetchRecruiterJobOffers();
      },
      error => {
        console.error('Error deleting job offer:', error);
      }
    );
  }
  prevPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.fetchRecruiterJobOffers();
    }
  }
  
  nextPage() {
    if (this.jobOffers && !this.jobOffers.last) { 
      this.currentPage++;
      this.fetchRecruiterJobOffers();
    }
  }

  fetchRecruiterJobOffers() {
    this.jobOfferService.getRecruiterJobOffers(this.currentPage, this.pageSize, this.searchQuery,this.authService.getUsername()).subscribe(
      jobOffers => {
        this.jobOffers = jobOffers;
      },
      error => {
        console.error('Error fetching job offers:', error);
      }
    );
  }

  onSearchChange() {
    this.fetchRecruiterJobOffers();
  }

  navigateToJobOffer(id: string) {
    this.router.navigate(['/my-job-offer', id]);
  }
  navigateToCreateJobOffer() {
    this.router.navigate(['/create-job-offer']);
}

}
