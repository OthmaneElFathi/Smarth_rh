import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyJobOfferComponent } from './my-job-offer.component';

describe('MyJobOfferComponent', () => {
  let component: MyJobOfferComponent;
  let fixture: ComponentFixture<MyJobOfferComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MyJobOfferComponent]
    });
    fixture = TestBed.createComponent(MyJobOfferComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
