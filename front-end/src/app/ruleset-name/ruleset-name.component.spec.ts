import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RulesetNameComponent } from './ruleset-name.component';

describe('RulesetNameComponent', () => {
  let component: RulesetNameComponent;
  let fixture: ComponentFixture<RulesetNameComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RulesetNameComponent]
    });
    fixture = TestBed.createComponent(RulesetNameComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create "ruleset-name" component', () => {
    expect(component).toBeTruthy();
  });
});