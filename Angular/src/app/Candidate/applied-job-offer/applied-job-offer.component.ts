import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JobOfferResponse } from 'src/app/types/job-offer.interface';
import { JobOfferService } from 'src/app/services/job-offer.service';
import { CandidateService } from 'src/app/services/candidate.service';

@Component({
  selector: 'app-applied-job-offer',
  templateUrl: './applied-job-offer.component.html',
  styleUrls: ['./applied-job-offer.component.scss']
})
export class AppliedJobOfferComponent implements OnInit {
  jobOffer: JobOfferResponse | undefined;

  constructor(private route: ActivatedRoute, private router: Router,private jobOfferService: JobOfferService,private candidateService:CandidateService) { }

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
  cancelApplication(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.candidateService.removeJobApplication(id).subscribe(
      () => {
        this.router.navigate(['/my-applications']);
      },
      error => {
        console.error('Error cancelling application:', error);
      }
    );
  }
}
