import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CourseChatComponent } from './course-chat/course-chat.component';

const routes: Routes = [
  { path: 'coursechat/:courseId', component: CourseChatComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
