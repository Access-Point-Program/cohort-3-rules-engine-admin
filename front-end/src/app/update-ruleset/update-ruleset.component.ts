import { AfterViewChecked, AfterViewInit, ChangeDetectorRef, Component, OnInit, QueryList, ViewChildren } from '@angular/core';
import { RulesComponentComponent } from '../rules-component/rules-component.component';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-update-ruleset',
  templateUrl: './update-ruleset.component.html',
  styleUrls: ['./update-ruleset.component.css']
})
export class UpdateRulesetComponent implements OnInit, AfterViewInit, AfterViewChecked {
  
  @ViewChildren(RulesComponentComponent) viewChildren!: QueryList<RulesComponentComponent>;
  public ruleset: RulesComponentComponent[] = [new RulesComponentComponent()]
  public name:string = '';
  public incomingThen: string = '';
  constructor(private changeDetectorRef: ChangeDetectorRef, private route: ActivatedRoute, private router: Router){}

  title = 'HTTP using native fetch API';
  private url: string = 'http://localhost:8080/ruleset/';

  
  paramsSubscription$!: Subscription;
  id!: string | null;

  ngOnInit() {
    this.paramsSubscription$ = this.route.paramMap.subscribe(
      (params: ParamMap) => {
        this.id = params.get("id");
        console.log(this.id);
        this.url += this.id;
        console.log(this.url);
    });
    fetch(this.url) 
    .then((response) => response.json())
    .then((response) => {
      console.log(response)
      const incomingRuleset: RulesComponentComponent[] = [];
      for(let i: number = 0; i < response.rules.length(); i++) {
        
      }
      this.ruleset = response.rules;
      this.name = response.name;
      console.log("ONINIT " + this.name);
    });
  }

  ngOnDestroy() {
    if (this.paramsSubscription$) this.paramsSubscription$.unsubscribe();
  }

  ngAfterViewInit(): void {
    console.log(this.ruleset);
    console.log(this.viewChildren.toArray());
    this.ruleset = this.viewChildren.toArray();
    this.changeDetectorRef.detectChanges();
  }

  ngAfterViewChecked() {
    for(let i = 0; i < this.ruleset.length; i++){
      if(this.ruleset[i].priority == undefined){
        this.ruleset[i].priority = this.viewChildren.toArray()[i].priority;
      }
    }
  }

    forceUpdateRuleset() {
      this.ruleset = this.viewChildren.toArray();
      this.ruleset.forEach(rule => {
        rule.childrenConditions = rule.viewChildren.toArray();
      })
    }
  
    onAddRuleClick() {
      this.ruleset.push(new RulesComponentComponent());
    }
  
    priorityMoveUp() {
      this.forceUpdateRuleset();
    }
    priorityMoveDown() {
      this.forceUpdateRuleset();
    }
  
    saveButtonClick():RulesComponentComponent[]{
      this.forceUpdateRuleset();
      return this.ruleset;
    }
  
    getName():string{
      return this.name;
    }
  
    public updateRuleset() {
      this.ruleset.sort(function(a, b) {
        const priorityA = a.priority;
        const priorityB = b.priority;
        return (priorityA < priorityB) ? -1 : (priorityA > priorityB) ? 1 : 0;
      });
    }
}
