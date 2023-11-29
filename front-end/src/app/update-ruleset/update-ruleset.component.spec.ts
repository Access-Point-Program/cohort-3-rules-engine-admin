import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateRulesetComponent } from './update-ruleset.component';

describe('UpdateRulesetComponent', () => {
  let component: UpdateRulesetComponent;
  let fixture: ComponentFixture<UpdateRulesetComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateRulesetComponent]
    });
    fixture = TestBed.createComponent(UpdateRulesetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
