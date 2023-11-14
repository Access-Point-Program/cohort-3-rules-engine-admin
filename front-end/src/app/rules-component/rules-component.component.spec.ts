import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RulesComponentComponent } from './rules-component.component';

describe('RulesComponentComponent', () => {
  let component: RulesComponentComponent;
  let fixture: ComponentFixture<RulesComponentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RulesComponentComponent]
    });
    fixture = TestBed.createComponent(RulesComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
