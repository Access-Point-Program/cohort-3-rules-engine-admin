import { Component } from '@angular/core';
import { RulesComponentComponent } from '../rules-component/rules-component.component';
import { Injector } from '@angular/core';

@Component({
  selector: 'app-move-up-button',
  templateUrl: './move-up-button.component.html',
  styleUrls: ['./move-up-button.component.css']
})
export class MoveUpButtonComponent {
  public _parent: RulesComponentComponent;

  constructor(private _injector: Injector) { 
    const _parent: RulesComponentComponent = this._injector.get<RulesComponentComponent>(RulesComponentComponent);
    this._parent = _parent;
  }

  public moveRuleUpInPriority() {
    console.log(this._parent.getPriority());
    
  }
}
