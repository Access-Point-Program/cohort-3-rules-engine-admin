import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteRuleButtonComponent } from './delete-rule-button.component';

describe('DeleteRuleButtonComponent', () => {
  let component: DeleteRuleButtonComponent;
  let fixture: ComponentFixture<DeleteRuleButtonComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DeleteRuleButtonComponent]
    });
    fixture = TestBed.createComponent(DeleteRuleButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
