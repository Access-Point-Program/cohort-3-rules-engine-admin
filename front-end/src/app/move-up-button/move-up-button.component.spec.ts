import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MoveUpButtonComponent } from './move-up-button.component';

import { RulesComponentComponent } from '../rules-component/rules-component.component';

describe('MoveUpButtonComponent', () => {
  let component: MoveUpButtonComponent;
  let fixture: ComponentFixture<MoveUpButtonComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MoveUpButtonComponent], 
      providers: [RulesComponentComponent]
    });
    fixture = TestBed.createComponent(MoveUpButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
