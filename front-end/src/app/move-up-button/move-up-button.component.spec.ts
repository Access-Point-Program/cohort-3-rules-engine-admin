import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MoveUpButtonComponent } from './move-up-button.component';
import { CreateRulesetComponent } from '../create-ruleset/create-ruleset.component';

describe('MoveUpButtonComponent', () => {
  let component: MoveUpButtonComponent;
  let fixture: ComponentFixture<MoveUpButtonComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        MoveUpButtonComponent
      ], 
      providers: [CreateRulesetComponent]
    });
    fixture = TestBed.createComponent(MoveUpButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create "move-up" component', () => {
    expect(component).toBeTruthy();
  });
});
