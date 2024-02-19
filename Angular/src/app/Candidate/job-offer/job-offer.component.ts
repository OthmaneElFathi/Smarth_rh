import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JobOfferResponse } from 'src/app/types/job-offer.interface';
import { JobOfferService } from 'src/app/services/job-offer.service';

@Component({
  selector: 'app-job-offer',
  templateUrl: './job-offer.component.html',
  styleUrls: ['./job-offer.component.scss']
})
export class JobOfferComponent implements OnInit {
  jobOffer: JobOfferResponse | undefined;

  constructor(private route: ActivatedRoute, private jobOfferService: JobOfferService) { }

  ngOnInit(): void {
    this.getJobOfferDetails();
  }

  getJobOfferDetails(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.jobOfferService.getJobOfferById(id).subscribe(
        jobOffer => {
          this.jobOffer = jobOffer;
        },
        error => {
          console.error('Error fetching job offer details:', error);
        }
      );
    }
  }
  applyToJobOffer(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.jobOfferService.applyToJobOffer(id).subscribe(
        () => {
          console.log('Successfully applied to job offer.');
        },
        error => {
          console.error('Error applying to job offer:', error);
        }
      );
    }
  }
}
