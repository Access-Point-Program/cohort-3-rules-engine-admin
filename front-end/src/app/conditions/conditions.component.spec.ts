import { ComponentFixture, TestBed } from '@angular/core/testing';
import { WhenComponent } from './when/when.component';
import { IsComponent } from './is/is.component';

import { ConditionsComponent } from './conditions.component';

describe('ConditionsComponent', () => {
  let component: ConditionsComponent;
  let fixture: ComponentFixture<ConditionsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ConditionsComponent, WhenComponent, IsComponent]
    });
    fixture = TestBed.createComponent(ConditionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create "conditions" component', () => {
    expect(component).toBeTruthy();
  });
});
