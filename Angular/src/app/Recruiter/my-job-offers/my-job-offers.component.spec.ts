import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyJobOffersComponent } from './my-job-offers.component';

describe('MyJobOffersComponent', () => {
  let component: MyJobOffersComponent;
  let fixture: ComponentFixture<MyJobOffersComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MyJobOffersComponent]
    });
    fixture = TestBed.createComponent(MyJobOffersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
