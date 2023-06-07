[<Back](./../README.md)

# BookWorm
Software Engineering Summer 2023 Project created by Team Honda (Group 13)

# Branching
There will be three main branches in this project: Main, Release, and Develop. All development of tasks is done in task branches that are made off of the develop branch. Once all of our Features for an iteration is complete, we will merge from Develop into Release. From there we will only tweak small changes or bug fixes in the release branch. Once we are completely finished with the Iteration, we merge from Releases and into the Main branch.

Task branches can be named with any format, however it is advised to be descriptive of the task's functionality

When your task is finished, you can put up a merge request to have your branch merged into the Develop branch. Other members must Code Review and Approve the code before merging.

# Merge Requests
All merge requests should have an appropriate title, consisting of a brief description of what was changed/added/done. In the description should be a more detailed version, including any and all important/relevant technical details.

You should input the time tracking spent by the collapsed sidebar. You should also label merge requests with developer task, in-review, and priority tags.

It is required in your merge request to check off `squash commits` and `delete source branch`. This must be done unless we are merging from Develop -> Release - > Main where we do not check off those options.

Each merge request must be approved and code reviews by at least one other member before merging.

For code reviewers, you must pull from the merging branch and run the allTest function before finally merging.

# Unit Tests
Members are required to write their own tests as they write their code. Code reviewers should review created tests in case edge cases were missed. Written tests must cover at least 80% lines covered, and ideally 90%+ line covered for any code in the logic layer. 
