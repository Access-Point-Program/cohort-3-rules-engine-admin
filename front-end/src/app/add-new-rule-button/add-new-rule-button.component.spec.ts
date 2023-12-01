import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AddNewRuleButtonComponent } from './add-new-rule-button.component';

describe('AddNewRuleButtonComponent', () => {
  let component: AddNewRuleButtonComponent;
  let fixture: ComponentFixture<AddNewRuleButtonComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddNewRuleButtonComponent]
    });
    fixture = TestBed.createComponent(AddNewRuleButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create "add-new-rule-button" component', () => {
    expect(component).toBeTruthy();
  });
});