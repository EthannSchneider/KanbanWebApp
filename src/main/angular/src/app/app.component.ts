import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
  public static title: string;
  public static rightButtonRedirect: string;
  public static rightButtonText: string;
  public static leftButtonRedirect: string;
  public static leftButtonText: string;

  ngOnInit() {
    AppComponent.title = '';
    AppComponent.rightButtonText = '';
    AppComponent.rightButtonRedirect = '';
    AppComponent.leftButtonText = '';
    AppComponent.leftButtonRedirect = '';
  }

  

  get title() {
    return AppComponent.title;
  }

  get rightButtonRedirect() {
    return AppComponent.rightButtonRedirect;
  }

  get rightButtonText() {
    return AppComponent.rightButtonText;
  }

  get leftButtonRedirect() {
    return AppComponent.leftButtonRedirect;
  }

  get leftButtonText() {
    return AppComponent.leftButtonText;
  }
}
