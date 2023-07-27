[<Back](./../README.md)

# Iteration 3 Worksheet

## What technical debt has been cleaned up

We recognized the technical debt arising from the limitation in our existing code, which prevented us from easily extending the system to handle different types of authors with varying abilities to write books.

We felt that this restriction could lead to potential slowdowns and significant refactoring efforts in the future if we ever needed to introduce new types of authors that do not have the ability to write books.

To address this technical debt, we decided to pay it off by creating a new method in the parent User class. This method allows us to determine whether a user or class have the ability to write a book or not. By making this improvement, we have made the code close to changes and open for extension as we can differentiate types of authors with distinct abilities in the future, thus reducing the risk of accumulating further technical debt and improving the overall maintainability of our system.

The changes can be seen in this commit:
https://code.cs.umanitoba.ca/3350-summer2023/teamhonda-13/-/commit/3fe0d1e4aa70b8b715e919011a535254df054464

We consider this technical debt to be both prudent and inadvertent. It is inadvertent because the limitation was unintentionally introduced during the initial coding phase, as we did not anticipate the need for accommodating new types of Authors or Users at that time. However, it is prudent because we recognized the issue and took the initiative to make improvements to our code.

## What technical debt did you leave?

One technical debt that we have identified and would like to address is related to the radio buttons used in our search feature. Currently, the implementation is not extendable, making it challenging to add new search categories without extensive modifications and refactoring throughout the codebase. Due to time constraints, we have left this technical debt unresolved for now.

We believe that this is an inadvertent and prudent technical debt. Inadvertent, as the problem was not intentionally introduced. We were unaware of a better approach at the time and did not realize the implications of not making the code open for extension. It is prudent because we have gained insights into what needs to be done to address the problem effectively.

Here is the technical debt that we leave: https://code.cs.umanitoba.ca/3350-summer2023/teamhonda-13/-/blob/main/app/src/main/java/honda/bookworm/View/Search_ViewHandler.java#L81

## Discuss a Feature or User Story that was cut/re-prioritized

As we were planning for iteration 3, we decided to shake things up a bit and cut the Book Ratings feature. Initially, we wanted users to review and rate books, but then we realized it was kinda similar to the comments and favorite books stuff we already had going on. So, we discuss about it and came up with something better – the Profile Navigator feature.

Now, instead of rating books, users can have a peek and explore other users' profiles. You get to check out what others are into, see their tastes, and maybe even find the books that they have written. It's a cool way to connect and discover, making our platform way more exciting and interactive.

Here is the Book Ratings feature: https://code.cs.umanitoba.ca/3350-summer2023/teamhonda-13/-/issues/8

And here is the new Profile Navigator feature: https://code.cs.umanitoba.ca/3350-summer2023/teamhonda-13/-/issues/135

## Acceptance test / end to end

Things that we tested:

- [Create User Profile](https://code.cs.umanitoba.ca/3350-summer2023/teamhonda-13/-/commit/88a8ba255dccca551e9ada569d03035bd2218d22#254a269e6d6709b9319c8fd858e1425ab6ee1ef3)
- [View Books](https://code.cs.umanitoba.ca/3350-summer2023/teamhonda-13/-/commit/88a8ba255dccca551e9ada569d03035bd2218d22#bb51665e17fad1239cd75fd02d6a14e8ffdc1779)
- [Book Details](https://code.cs.umanitoba.ca/3350-summer2023/teamhonda-13/-/commit/88a8ba255dccca551e9ada569d03035bd2218d22#a79bcb602689dd298574df3fa3c75179c98ff0f6)
- [Favorite Book](https://code.cs.umanitoba.ca/3350-summer2023/teamhonda-13/-/commit/88a8ba255dccca551e9ada569d03035bd2218d22#d731f718becc72db8908385bd90a605df924ca87)
- [Adding Books](https://code.cs.umanitoba.ca/3350-summer2023/teamhonda-13/-/commit/88a8ba255dccca551e9ada569d03035bd2218d22#8cc1fa36c7dd2daec34d67b4fd4250f3436aa5f6)
- [Search Books](https://code.cs.umanitoba.ca/3350-summer2023/teamhonda-13/-/commit/88a8ba255dccca551e9ada569d03035bd2218d22#58951ab47331ce89a1aa381cc0f21cbef34bbe83)
- [Open User Profile](https://code.cs.umanitoba.ca/3350-summer2023/teamhonda-13/-/commit/88a8ba255dccca551e9ada569d03035bd2218d22#a2f989f67f4c00c9266a6298a31f1fee8983fcc4)
- [View Book Images](https://code.cs.umanitoba.ca/3350-summer2023/teamhonda-13/-/commit/88a8ba255dccca551e9ada569d03035bd2218d22#4e50d599e050bf29539ca1b84128099a8f6549f4)

To ensure stable and reliable tests, we implement setup and cleanup procedures after each test using the @Before and @After annotations. By utilizing @Before, we can set up the database to a "reset" state before each test runs. This ensures that each test starts with a clean slate and avoids interference from previous test runs. On the other hand, @After is employed to clean up the database after testing, leaving it in a consistent state and preventing any lingering test data from affecting subsequent tests.

Furthermore, to enhance test consistency, we use a dedicated user account exclusively for testing purposes. This approach ensures that the test environment remains isolated from real user data and interactions, reducing the risk of unexpected behavior due to external factors. By utilizing a specific testing account, we can better control the test scenarios and achieve more predictable outcomes.

An example of the test that fulfill those requirements can be seen [here](https://code.cs.umanitoba.ca/3350-summer2023/teamhonda-13/-/commit/e8134b014bb22566f43d3b18f2dd6d0d5517100e#8cc1fa36c7dd2daec34d67b4fd4250f3436aa5f6), during the Adding Books tests.

## Acceptance test, untestable

During the creation of acceptance tests, we encountered various challenges, such as ensuring test stability and compatibility across multiple devices. Making the tests non-flaky proved to be a tricky task, as we needed to handle potential inconsistencies in the test environment to achieve reliable and repeatable results. Additionally, ensuring that the tests ran smoothly on different devices required careful consideration of varying screen sizes, resolutions, and operating systems.

One feature, the [purchase book](https://code.cs.umanitoba.ca/3350-summer2023/teamhonda-13/-/issues/3) feature, caused a unique problem. Since it redirects users to another tab or page, we don't have much control, making it impossible to test through automation.

## Velocity/teamwork

After examining the [Velocity Graph](.\docs\Velocity.png), we noticed definite improvements in our estimations. As we expect a significant workload in the second sprint, we have decided to allocate more time for estimation to ensure realistic timeframes and successful project completion. As a result, we anticipate better time estimation results in iteration 2 compared to iteration 1, as reflected in the completion of all committed work.
