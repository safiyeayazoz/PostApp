# PostApp

This app is post viewing application. Basically, it shows post list with post title and 120 character of post text.
When you click on any post item you can see post details. It include post title, all post text, post owner name and post owner username.

# What are they in project as a technical:
- MVVM architecture 
- Jetpack compose
- Navigation
- MutableState
- Pagination
- Error Interceptor
- Unit tests

PostListFragment -> Post list view and click on post item responsibility
PostListViewModel -> Taking post list from repository 
                     Listen user scrolling action and get next 10 post list from api by scrolling position

PostDetailFragment -> Post detail view responsibility
PostDetailViewModel -> Handle bundle data which sent from previous fagment (PostListFragment)
                    -> Get post owner data from api and show on screen
                 
                    
<img width="300" alt="image" src="https://user-images.githubusercontent.com/38051809/146745221-05a81a82-623a-4146-8da5-b56cf5b6e270.jpeg">             <img width="300" alt="image" src="https://user-images.githubusercontent.com/38051809/146745600-09a45107-a01c-44fb-8222-c782c1edbf5e.jpeg"> 


                    
                    
