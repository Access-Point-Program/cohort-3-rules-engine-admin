import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MoveDownButtonComponent } from './move-down-button.component';
import { CreateRulesetComponent } from '../create-ruleset/create-ruleset.component';

describe('MoveDownButtonComponent', () => {
  let component: MoveDownButtonComponent;
  let fixture: ComponentFixture<MoveDownButtonComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        MoveDownButtonComponent
      ],
      providers: [CreateRulesetComponent]
    });
    fixture = TestBed.createComponent(MoveDownButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create "move-down" component', () => {
    expect(component).toBeTruthy();
  });
});