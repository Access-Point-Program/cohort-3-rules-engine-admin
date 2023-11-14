import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateNewConditionComponent } from './create-new-condition.component';

describe('CreateNewConditionComponent', () => {
  let component: CreateNewConditionComponent;
  let fixture: ComponentFixture<CreateNewConditionComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CreateNewConditionComponent]
    });
    fixture = TestBed.createComponent(CreateNewConditionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
