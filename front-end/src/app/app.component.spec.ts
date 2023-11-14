import { TestBed } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { WhenComponent } from './conditions/when/when.component';
import { IsComponent } from './conditions/is/is.component';
import { ThenComponent } from './then/then.component';
import { ConditionsComponent } from './conditions/conditions.component';
import { SidebarComponent } from './sidebar/sidebar.component';

describe('AppComponent', () => {
  beforeEach(() => TestBed.configureTestingModule({
    declarations: [AppComponent,
      WhenComponent,
      IsComponent,
      ThenComponent,
      ConditionsComponent, 
      SidebarComponent
    ]
  }));

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'front-end'`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('front-end');
  });
});
