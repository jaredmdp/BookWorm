[<Back](./../README.md)

# Iteration 2 Worksheet

## Paying off technical debt
# Technical debt 1: Add Books Refactor
We felt this was technical debt as the logic for how addBook functioned was different from addUser and needed to be used differently.
We felt this may cause slowdowns in the future as the inconsistency between our 2 add functions meant devs would need to understand and learn 2 different functions.
They would also have to remember how each is used differently and this may lead to mistakes where 1 add function is used the way the other one is coded.
This further expanded to the front end where data was being passed to each differently meaning the front end code to each also looked slightly different

We payed off this technical debt by refactoring addBook to be used in the same was as addUser in this commit https://code.cs.umanitoba.ca/3350-summer2023/teamhonda-13/-/merge_requests/89/diffs?commit_id=c9321970ffc676d090aa4764a0a47d47c0c17328#2f052f97b2e1ae76d9d391962e439fee7445a879_40_41

This was reckless inadvertent tech debt. 
Inadvertent as it was unintentionally done by the 2 different people that initially set up the 2 functions.
Reckless as the issue stemmed from a lack of coordination, communication, and planning from the team.

# Technical debt 2: Improving Exception descriptiveness
This was technical debt as it caused problems in development where we needed to very carefully examine what was being thrown to resolve bugs.
Particularly we would have cases where it was not clear which exceptions were being caught or thrown since they were all generically 1 exception.

We payed off this technical debt by creating specific exceptions for identified situations in this commit: https://code.cs.umanitoba.ca/3350-summer2023/teamhonda-13/-/merge_requests/83/diffs?commit_id=93b6ca109e3a8c97f5ed91e7577d1bc5586774d3
And applying the new exceptions in this commit: https://code.cs.umanitoba.ca/3350-summer2023/teamhonda-13/-/merge_requests/83/diffs?commit_id=179992b67f5004288688b64fa535faea6edc211f

This was prudent inadvertent tech debt.
Inadvertent as we weren't aware of this sort of dev issues when first coding exceptions and didn't understand we were creating these issues
Prudent as we took the time to carefully implement what we thought (at the time) was good exception handling

## SOLID
Link to the issue created for SOLID violations of group 1:
https://code.cs.umanitoba.ca/3350-summer2023/rob-kebabs-123/-/issues/65

## Retrospective
During our retrospective we discussed more members touching/doing work on the front end. To assist with this our primary UI member conducted a UI bootcamp of sorts.
Since then, most members have now touched the front end in some capacity seen in these PRs:
https://code.cs.umanitoba.ca/3350-summer2023/teamhonda-13/-/merge_requests/87/diffs?commit_id=c6df3b416c41b589f121b3d67f4bb66b42f39627
https://code.cs.umanitoba.ca/3350-summer2023/teamhonda-13/-/merge_requests/86/diffs?commit_id=4fe26f0e3803ab5ae47d9a124d2776aa5b63111b
https://code.cs.umanitoba.ca/3350-summer2023/teamhonda-13/-/merge_requests/85/diffs?commit_id=deb7735683978887ac4986872f2d6318d3f351ef#1d0aea24ef17a41c7b07116f5e2e76df32eb3ff7

During our retrospective we also discussed various changes to help better organize our repository.
Changes include: 
Using the built in link feature from gitlab so links are better to manage rather then in iteration 1 where we did linking manually in the description. Task with proper links https://code.cs.umanitoba.ca/3350-summer2023/teamhonda-13/-/issues/123
Using estimate in gitlab to get a better visual representation of how our time spent compares to time estimated. Estimated time being auto-summed and compared in the milestone page https://code.cs.umanitoba.ca/3350-summer2023/teamhonda-13/-/milestones/3#tab-issues
Using built in gitlab branch creator to achieve consistent branch naming across all team member branches. Example PR for branch with auto-generated name https://code.cs.umanitoba.ca/3350-summer2023/teamhonda-13/-/merge_requests/91

## Design patterns
In this commit we are using the singleton design pattern to handle data layer access
https://code.cs.umanitoba.ca/3350-summer2023/teamhonda-13/-/merge_requests/70/diffs?commit_id=d3e94bda035cf2f76606540f77b0ec040d24239e

By using the static functions in Services, only a single instance of the Db access point is created and returned, if an instance already exists
The code is designed such that the existing instance is returned rather then a new one being made
This is a singleton since only 1 instance of the Db access exists throughout the program and Services provides global access to this single instance

## Iteration 1 Feedback fixes
Issue opened by the grader: https://code.cs.umanitoba.ca/3350-summer2023/teamhonda-13/-/issues/78

The issue was with our user Sign-up form. We didn't have appropriate validation for user input. This resulted in users being able to have inputs that aren't appropriate such as having numbers in First and Last names. Additionally, users can also insert dangerous characters such as "/" which can potentially lead to unwanted behavior.

To fix this issue we implemented a String Validator class which help us with validating the characters from the user input. We also implemented private validator functions in AccessUsers which throws an error when there is input violations.

The commit for the fix can be found here:
https://code.cs.umanitoba.ca/3350-summer2023/teamhonda-13/-/merge_requests/59/diffs?commit_id=a974b35ee1b4c949c670727b13061e6b424b105e