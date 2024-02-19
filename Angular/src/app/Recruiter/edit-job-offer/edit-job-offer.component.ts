import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JobOfferService } from '../../services/job-offer.service';
import { JobOfferRequest } from '../../types/job-offer.interface';

@Component({
  selector: 'app-edit-job-offer',
  templateUrl: './edit-job-offer.component.html',
  styleUrls: ['./edit-job-offer.component.scss']
})
export class EditJobOfferComponent implements OnInit {
  jobOffer: JobOfferRequest = { name: '', description: '', daysUntilExpired: 0, requiredSkills: [] } ; 
  skill: string = '';
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private jobOfferService: JobOfferService
  ) { }

  ngOnInit(): void {
    const jobId = this.route.snapshot.params['id'];
    this.jobOfferService.getJobOfferById(jobId).subscribe(
      (data: any) => {
        this.jobOffer = data;
      },
      error => {
        console.error('Error fetching job offer:', error);
        
      }
    );
  }
  removeSkill(skill: string): void {
    const index = this.jobOffer.requiredSkills.indexOf(skill);
    if (index !== -1) {
      this.jobOffer.requiredSkills.splice(index, 1);
    }
  }
  addSkill() {
    if (this.skill.trim() !== '' && !this.jobOffer.requiredSkills.includes(this.skill.trim())) {
      this.jobOffer.requiredSkills.push(this.skill.trim());
      this.skill = '';
    }
  }

  onSubmit() {
    const jobId = this.route.snapshot.params['id'];
    if (this.jobOffer !== null) {
    this.jobOfferService.editJobOffer(jobId, this.jobOffer).subscribe(
      () => {
        this.router.navigate(['/job-offers']);
      },
      error => {
        console.error('Error updating job offer:', error);
        
      }
    );
  }else {
    
    console.error('Job offer is null.');
  }
}
}
