import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-ruleset-name',
  templateUrl: './ruleset-name.component.html',
  styleUrls: ['./ruleset-name.component.css']
})
export class RulesetNameComponent implements OnInit {
  @Input() name!: string;
  @Output() nameChange = new EventEmitter<string>();

  ngOnInit() {
    console.log("ON INIT ruleset-name " + this.name);
  }

  ngOnChanges() {
    console.log("ON CHANGES ruleset-name " + this.name);
  }

  public setName(returnedValue: any): void {
    this.name = returnedValue.target.value;
    this.nameChange.emit(this.name);
  };
  
}