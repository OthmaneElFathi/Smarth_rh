import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms'; 
import { JobOfferService } from '../../services/job-offer.service';
@Component({
  selector: 'app-create-job-offer',
  templateUrl: './create-job-offer.component.html',
  styleUrls: ['./create-job-offer.component.scss']
})
export class CreateJobOfferComponent {
  jobOfferForm: FormGroup; 
  
  
  jobOfferData = {
    name: '',
    description: '',
    daysUntilExpired: 0,
    requiredSkills: [] as string[] 
  };
  skill: string = '';
  creating = false;

  constructor(private fb: FormBuilder,private jobOfferService: JobOfferService) {
    this.jobOfferForm = this.fb.group({ 
      name: ['', Validators.required],
      description: ['', Validators.required],
      daysUntilExpired: [0, Validators.required],
      requiredSkills: [[]]
    });
  }

  removeSkill(skill: string): void {
    const index = this.jobOfferData.requiredSkills.indexOf(skill);
    if (index !== -1) {
      this.jobOfferData.requiredSkills.splice(index, 1);
    }
  }

  onSubmit() {
    this.creating = true;
    this.jobOfferService.createJobOffer(this.jobOfferData).subscribe(
      response => {
        console.log('Job offer created successfully:', response);
        this.creating = false;
      },
      error => {
        console.error('Error creating job offer:', error);
        this.creating = false;
      }
    );
  }

  addSkill() {
    if (this.skill.trim() !== '' && !this.jobOfferData.requiredSkills.includes(this.skill.trim())) {
      this.jobOfferData.requiredSkills.push(this.skill.trim());
      this.skill = '';
    }
  }
  
}
