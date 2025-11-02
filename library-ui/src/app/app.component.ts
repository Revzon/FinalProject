import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { LibraryComponentComponent } from './components/library-component/library-component.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, LibraryComponentComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'library-ui';
}
