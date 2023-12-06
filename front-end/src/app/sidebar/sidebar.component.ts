import { Component } from '@angular/core';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {
  leavePage(url: string){
    if (confirm("Are you sure you want to leave?\nAny changes won't be saved!")) {
      window.location.href = url;  
    }
  }
}