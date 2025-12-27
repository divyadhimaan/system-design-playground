# Design Youtube-like Video Streaming Service

This problem is designing a video sharing/streaming service and can be applied to netflix, youtube, vimeo, udemy, etc.

The scope of this problem is very wide
  - Watching a video
  - Uploading a video
  - Commenting/Liking/ Sharing a video
  - Saving a video to playlist
  - Subscribe to a channel
and a lot more features can be added.

> Very important: Before jumping to design, make sure to gather requirements and clarify the scope of the problem.

## Step 1: Understand problem statement and requirements

> Candidate: What features are important?
> 
> Interviewer: Ability to upload a video and watch a video. 

> Candidate: What clients do we need to support? 
> 
> Interviewer: Mobile apps, web browsers, and smart TV. 

> Candidate: How many daily active users do we have? 
> 
> Interviewer: 5 million 

> Candidate: What is the average daily time spent on the product? 
> 
> Interviewer: 30 minutes. 

> Candidate: Do we need to support international users? 
> 
> Interviewer: Yes, a large percentage of users are international users. 

> Candidate: What are the supported video resolutions? 
> 
> Interviewer: The system accepts most of the video resolutions and formats.

> Candidate: Is encryption required? 
> 
> Interviewer: Yes

> Candidate: Any file size requirement for videos? 
> 
> Interviewer: Our platform focuses on small and medium-sized videos. The maximum
allowed video size is 1GB. 

> Candidate: Can we leverage some of the existing cloud infrastructures provided by Amazon,
Google, or Microsoft? 
> 
> Interviewer: That is a great question. Building everything from scratch is unrealistic for most
companies, it is recommended to leverage some of the existing cloud services.
