import { Component, Input, OnInit, input } from '@angular/core';

@Component({
  selector: 'app-board-backlog',
  templateUrl: './board-backlog.component.html',
  styleUrl: './board-backlog.component.scss'
})
export class BoardBacklogComponent implements OnInit{
   @Input() currentPage: string = "";
   @Input() boardRedirect: string = "";
    @Input() backlogRedirect: string = "";

    constructor() { }

    ngOnInit(): void {
      document.getElementById(this.currentPage)?.classList.add('selected-item')
    }
}
