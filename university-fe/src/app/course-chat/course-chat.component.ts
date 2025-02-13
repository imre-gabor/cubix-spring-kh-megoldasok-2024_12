import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Client, StompHeaders } from '@stomp/stompjs';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-course-chat',
  templateUrl: './course-chat.component.html',
  styleUrls: ['./course-chat.component.scss']
})
export class CourseChatComponent implements OnInit {

  courseId: Number;
  stompClient: Client;
  sender: string;
  text: string;
  chatMessages: string[] = [];


  constructor(
    private route: ActivatedRoute,
    private authService: AuthService) { }

  ngOnInit(): void {
    this.courseId = Number(this.route.snapshot.paramMap.get('courseId'));
    this.connect();
  }

  connect() {


    this.stompClient = new Client({
      brokerURL: '/api/stomp',
      connectHeaders: {'X-Authorization' : 'Bearer ' + this.authService.getToken()},
      onConnect: () => {
        this.subscribeToCourseChat();
      }
    });
    this.stompClient.activate();
  }

  sendMessage() {
    this.stompClient.publish({
      "destination": '/app/chat',
      "body": JSON.stringify({
        "sender": this.sender,
        "courseId": this.courseId,
        "text": this.text
      })
    });      
  }

  subscribeToCourseChat() {
    this.stompClient.subscribe('/topic/courseChat/' + this.courseId, 
        message => {
          const senderAndText = message.body;
          this.chatMessages.push(senderAndText);
        }
    );
  }
  
}
