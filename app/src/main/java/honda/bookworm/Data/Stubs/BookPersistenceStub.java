package honda.bookworm.Data.Stubs;

import java.util.ArrayList;
import java.util.List;

import honda.bookworm.Data.IBookPersistence;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;

public class BookPersistenceStub implements IBookPersistence {
    private List<Book> books;

    public BookPersistenceStub() {
        this.books = new ArrayList<>();

        //Fantasy
        books.add(new Book("The Way of Kings", "Brandon Sanderson", Genre.Fantasy, "9780765326355",
                "From #1 New York Times bestselling author Brandon Sanderson, The Way of Kings, book one of The Stormlight Archive begins an incredible new saga of epic proportion.\n" +
                        "\n" +
                        "Roshar is a world of stone and storms. Uncanny tempests of incredible power sweep across the rocky terrain so frequently that they have shaped ecology and civilization alike. Animals hide in shells, trees pull in branches, and grass retracts into the soilless ground. Cities are built only where the topography offers shelter.\n" +
                        "\n" +
                        "It has been centuries since the fall of the ten consecrated orders known as the Knights Radiant, but their Shardblades and Shardplate remain: mystical swords and suits of armor that transform ordinary men into near-invincible warriors. Men trade kingdoms for Shardblades. Wars were fought for them, and won by them.\n" +
                        "\n" +
                        "One such war rages on a ruined landscape called the Shattered Plains. There, Kaladin, who traded his medical apprenticeship for a spear to protect his little brother, has been reduced to slavery. In a war that makes no sense, where ten armies fight separately against a single foe, he struggles to save his men and to fathom the leaders who consider them expendable.\n" +
                        "\n" +
                        "Brightlord Dalinar Kholin commands one of those other armies. Like his brother, the late king, he is fascinated by an ancient text called The Way of Kings. Troubled by over-powering visions of ancient times and the Knights Radiant, he has begun to doubt his own sanity.\n" +
                        "\n" +
                        "Across the ocean, an untried young woman named Shallan seeks to train under an eminent scholar and notorious heretic, Dalinar's niece, Jasnah. Though she genuinely loves learning, Shallan's motives are less than pure. As she plans a daring theft, her research for Jasnah hints at secrets of the Knights Radiant and the true cause of the war.\n" +
                        "\n" +
                        "The result of over ten years of planning, writing, and world-building, The Way of Kings is but the opening movement of the Stormlight Archive, a bold masterpiece in the making.\n" +
                        "\n" +
                        "Speak again the ancient oaths:\n" +
                        "\n" +
                        "Life before death.\n" +
                        "Strength before weakness.\n" +
                        "Journey before Destination.\n" +
                        "\n" +
                        "and return to men the Shards they once bore.\n" +
                        "\n" +
                        "The Knights Radiant must stand again."
        ));

        books.add(new Book("Mistborn", "Brandon Sanderson", Genre.Fantasy, "9780765350381",
                "What if the whole world were a dead, blasted wasteland?\n" +
                        "\n" +
                        "Mistborn\n" +
                        "For a thousand years the ash fell and no flowers bloomed. For a thousand years the Skaa slaved in misery and lived in fear. For a thousand years the Lord Ruler, the \"Sliver of Infinity,\" reigned with absolute power and ultimate terror, divinely invincible. Then, when hope was so long lost that not even its memory remained, a terribly scarred, heart-broken half-Skaa rediscovered it in the depths of the Lord Ruler's most hellish prison. Kelsier \"snapped\" and found in himself the powers of a Mistborn. A brilliant thief and natural leader, he turned his talents to the ultimate caper, with the Lord Ruler himself as the mark.\n" +
                        "\n" +
                        "Kelsier recruited the underworld's elite, the smartest and most trustworthy allomancers, each of whom shares one of his many powers, and all of whom relish a high-stakes challenge. Then Kelsier reveals his ultimate dream, not just the greatest heist in history, but the downfall of the divine despot.\n" +
                        "\n" +
                        "But even with the best criminal crew ever assembled, Kel's plan looks more like the ultimate long shot, until luck brings a ragged girl named Vin into his life. Like him, she's a half-Skaa orphan, but she's lived a much harsher life. Vin has learned to expect betrayal from everyone she meets. She will have to learn trust if Kel is to help her master powers of which she never dreamed.\n" +
                        "\n" +
                        "Brandon Sanderson, fantasy's newest master tale-spinner and author of the acclaimed debut Elantris, dares to turn a genre on its head by asking a simple question: What if the prophesied hero failed to defeat the Dark Lord? The answer will be found in the Misborn Trilogy, a saga of surprises that begins with the book in your hands. Fantasy will never be the same again."
        ));

        books.add(new Book("Words of Radiance", "Brandon Sanderson", Genre.Fantasy, "9780765326362",
                "Words of Radiance, Book Two of the Stormlight Archive, continues the immersive fantasy epic that The Way of Kings began.\n" +
                        "\n" +
                        "Expected by his enemies to die the miserable death of a military slave, Kaladin survived to be given command of the royal bodyguards, a controversial first for a low-status \"darkeyes.\" Now he must protect the king and Dalinar from every common peril as well as the distinctly uncommon threat of the Assassin, all while secretly struggling to master remarkable new powers that are somehow linked to his honorspren, Syl.\n" +
                        "\n" +
                        "The Assassin, Szeth, is active again, murdering rulers all over the world of Roshar, using his baffling powers to thwart every bodyguard and elude all pursuers. Among his prime targets is Highprince Dalinar, widely considered the power behind the Alethi throne. His leading role in the war would seem reason enough, but the Assassin's master has much deeper motives.\n" +
                        "\n" +
                        "Brilliant but troubled Shallan strives along a parallel path. Despite being broken in ways she refuses to acknowledge, she bears a terrible burden: to somehow prevent the return of the legendary Voidbringers and the civilization-ending Desolation that will follow. The secrets she needs can be found at the Shattered Plains, but just arriving there proves more difficult than she could have imagined.\n" +
                        "\n" +
                        "Meanwhile, at the heart of the Shattered Plains, the Parshendi are making an epochal decision. Hard pressed by years of Alethi attacks, their numbers ever shrinking, they are convinced by their war leader, Eshonai, to risk everything on a desperate gamble with the very supernatural forces they once fled. The possible consequences for Parshendi and humans alike, indeed, for Roshar itself, are as dangerous as they are incalculable."
        ));

        books.add(new Book("Elantris", "Brandon Sanderson", Genre.Fantasy, "9780765311788",
                "Elantris was the capital of Arelon: gigantic, beautiful, literally radiant, filled with benevolent beings who used their powerful magical abilities for the benefit of all. Yet each of these demigods was once an ordinary person until touched by the mysterious transforming power of the Shaod. Ten years ago, without warning, the magic failed. Elantrians became wizened, leper-like, powerless creatures, and Elantris itself dark, filthy, and crumbling.\n" +
                        "\n" +
                        "Arelon's new capital, Kae, crouches in the shadow of Elantris. Princess Sarene of Teod arrives for a marriage of state with Crown Prince Raoden, hoping—based on their correspondence—to also find love. She finds instead that Raoden has died and she is considered his widow. Both Teod and Arelon are under threat as the last remaining holdouts against the imperial ambitions of the ruthless religious fanatics of Fjordell. So Sarene decides to use her new status to counter the machinations of Hrathen, a Fjordell high priest who has come to Kae to convert Arelon and claim it for his emperor and his god.\n" +
                        "\n" +
                        "But neither Sarene nor Hrathen suspect the truth about Prince Raoden. Stricken by the same curse that ruined Elantris, Raoden was secretly exiled by his father to the dark city. His struggle to help the wretches trapped there begins a series of events that will bring hope to Arelon, and perhaps reveal the secret of Elantris itself.\n" +
                        "\n" +
                        "A rare epic fantasy that doesn't recycle the classics and that is a complete and satisfying story in one volume, Elantris is fleet and fun, full of surprises and characters to care about. It's also the wonderful debut of a welcome new star in the constellation of fantasy."
        ));

        books.add(new Book("The Alloy of Law", "Brandon Sanderson", Genre.Fantasy, "9780765368546",
                "Three hundred years after the events of the Mistborn trilogy, Scadrial is now on the verge of modernity, with railroads to supplement the canals, electric lighting in the streets and the homes of the wealthy, and the first steel-framed skyscrapers racing for the clouds.\n" +
                        "\n" +
                        "Kelsier, Vin, Elend, Sazed, Spook, and the rest are now part of history—or religion. Yet even as science and technology are reaching new heights, the old magics of Allomancy and Feruchemy continue to play a role in this reborn world. Out in the frontier lands known as the Roughs, they are crucial tools for the brave men and women attempting to establish order and justice.\n" +
                        "\n" +
                        "One such is Waxillium Ladrian, a rare Twinborn who can Push on metals with his Allomancy and use Feruchemy to become lighter or heavier at will.\n" +
                        "\n" +
                        "After twenty years in the Roughs, Wax has been forced by family tragedy to return to the metropolis of Elendel. Now he must reluctantly put away his guns and assume the duties and dignity incumbent upon the head of a noble house. Or so he thinks, until he learns the hard way that the mansions and elegant tree-lined streets of the city can be even more dangerous than the dusty plains of the Roughs."
        ));


        //Adult
        books.add(new Book("The Last Wish", "Andrzej Sapkowski", Genre.Adult, "0575077832",
                "Geralt the Witcher—revered and hated—is a man whose magic powers, enhanced by long training and a mysterious elixir, have made him a brilliant fighter and a merciless assassin. Yet he is no ordinary murderer: his targets are the multifarious monsters and vile fiends that ravage the land and attack the innocent.\n" +
                        "\n" +
                        "But not everything monstrous-looking is evil and not everything fair is good... and in every fairy tale there is a grain of truth."
        ));

        books.add(new Book("Blood of Elves", "Andrzej Sapkowski", Genre.Adult, "9780316029193",
                "The New York Times bestselling series that inspired the international hit video game: The Witcher.\n" +
                        "For over a century, humans, dwarves, gnomes, and elves have lived together in relative peace. But times have changed, the uneasy peace is over, and now the races are fighting once again. The only good elf, it seems, is a dead elf.\n" +
                        "\n" +
                        "Geralt of Rivia, the cunning assassin known as The Witcher, has been waiting for the birth of a prophesied child. This child has the power to change the world - for good, or for evil.\n" +
                        "\n" +
                        "As the threat of war hangs over the land and the child is hunted for her extraordinary powers, it will become Geralt's responsibility to protect them all - and the Witcher never accepts defeat.\n" +
                        "\n" +
                        "The Witcher returns in this sequel to The Last Wish, as the inhabitants of his world become embroiled in a state of total war."
        ));

        books.add(new Book("Sword of Destiny", "Andrzej Sapkowski", Genre.Adult, "970575077832",
                "Geralt is a witcher, a man whose magic powers, enhanced by long training and a mysterious elixir, have made him a brilliant fighter and a merciless assassin. Yet he is no ordinary murderer: his targets are the multifarious monsters and vile fiends that ravage the land and attack the innocent.\n" +
                        "\n" +
                        "This is a collection of short stories, following the adventures of the hit collection THE LAST WISH. Join Geralt as he battles monsters, demons and prejudices alike..."
        ));

        books.add(new Book("The Time of Contempt", "Andrzej Sapkowski", Genre.Adult, "0316219134",
                "The New York Times bestselling series that inspired the international hit video game: The Witcher\n" +
                        "Geralt is a witcher: guardian of the innocent; protector of those in need; a defender, in dark times, against some of the most frightening creatures of myth and legend. His task, now, is to protect Ciri. A child of prophecy, she will have the power to change the world for good or for ill -- but only if she lives to use it.\n" +
                        "\n" +
                        "A coup threatens the Wizard's Guild.\n" +
                        "War breaks out across the lands.\n" +
                        "A serious injury leaves Geralt fighting for his life...\n" +
                        "... and Ciri, in whose hands the world's fate rests, has vanished...\n" +
                        "\n" +
                        "The Witcher returns in this sequel to Blood of Elves."
        ));


        //Fiction
        books.add(new Book("1984", "George Orwell", Genre.Fiction, "9780452284234",
                "The new novel by George Orwell is the major work towards which all his previous writing has pointed. Critics have hailed it as his \"most solid, most brilliant\" work. Though the story of Nineteen Eighty-Four takes place thirty-five years hence, it is in every sense timely. The scene is London, where there has been no new housing since 1950 and where the city-wide slums are called Victory Mansions. Science has abandoned Man for the State. As every citizen knows only too well, war is peace.\n" +
                        "\n" +
                        "To Winston Smith, a young man who works in the Ministry of Truth (Minitru for short), come two people who transform this life completely. One is Julia, whom he meets after she hands him a slip reading, \"I love you.\" The other is O'Brien, who tells him, \"We shall meet in the place where there is no darkness.\" The way in which Winston is betrayed by the one and, against his own desires and instincts, ultimately betrays the other, makes a story of mounting drama and suspense."
        ));

        books.add(new Book("To Kill a Mockingbird", "Harper Lee", Genre.Fiction, "92345678909",
                "The unforgettable novel of a childhood in a sleepy Southern town and the crisis of conscience that rocked it. \"To Kill A Mockingbird\" became both an instant bestseller and a critical success when it was first published in 1960. It went on to win the Pulitzer Prize in 1961 and was later made into an Academy Award-winning film, also a classic.\n" +
                        "\n" +
                        "Compassionate, dramatic, and deeply moving, \"To Kill A Mockingbird\" takes readers to the roots of human behavior - to innocence and experience, kindness and cruelty, love and hatred, humor and pathos. Now with over 18 million copies in print and translated into forty languages, this regional story by a young Alabama woman claims universal appeal. Harper Lee always considered her book to be a simple love story. Today it is regarded as a masterpiece of American literature."
        ));

        books.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", Genre.Fiction, "9780743273565",
                "The Great Gatsby, F. Scott Fitzgerald's third book, stands as the supreme achievement of his career. This exemplary novel of the Jazz Age has been acclaimed by generations of readers. The story of the fabulously wealthy Jay Gatsby and his love for the beautiful Daisy Buchanan, of lavish parties on Long Island at a time when The New York Times noted \"gin was the national drink and sex the national obsession,\" it is an exquisitely crafted tale of America in the 1920s.\n" +
                        "\n" +
                        "The Great Gatsby is one of the great classics of twentieth-century literature."
        ));

        books.add(new Book("Animal Farm", "George Orwell", Genre.Fiction, "9780451526342",
                "A farm is taken over by its overworked, mistreated animals. With flaming idealism and stirring slogans, they set out to create a paradise of progress, justice, and equality. Thus the stage is set for one of the most telling satiric fables ever penned –a razor-edged fairy tale for grown-ups that records the evolution from revolution against tyranny to a totalitarianism just as terrible.\n" +
                        "When Animal Farm was first published, Stalinist Russia was seen as its target. Today it is devastatingly clear that wherever and whenever freedom is attacked, under whatever banner, the cutting clarity and savage comedy of George Orwell’s masterpiece have a meaning and message still ferociously fresh."
        ));

        books.add(new Book("Harry Potter and the Philosopher’s Stone","J.K. Rowling", Genre.Fiction, "9780747532743.",
                "Harry Potter thinks he is an ordinary boy - until he is rescued by an owl, taken to Hogwarts School of Witchcraft and Wizardry, learns to play Quidditch and does battle in a deadly duel. The Reason ... HARRY POTTER IS A WIZARD!"
        ));

        books.add(new Book("The Catcher in the Rye", "J.D. Salinger", Genre.Fiction,"9780316769174",
                "It's Christmas time and Holden Caulfield has just been expelled from yet another school...\n" +
                        "\n" +
                        "Fleeing the crooks at Pencey Prep, he pinballs around New York City seeking solace in fleeting encounters—shooting the bull with strangers in dive hotels, wandering alone round Central Park, getting beaten up by pimps and cut down by erstwhile girlfriends. The city is beautiful and terrible, in all its neon loneliness and seedy glamour, its mingled sense of possibility and emptiness. Holden passes through it like a ghost, thinking always of his kid sister Phoebe, the only person who really understands him, and his determination to escape the phonies and find a life of true meaning.\n" +
                        "\n" +
                        "The Catcher in the Rye is an all-time classic in coming-of-age literature- an elegy to teenage alienation, capturing the deeply human need for connection and the bewildering sense of loss as we leave childhood behind."
        ));


        //Survival
        books.add(new Book("The Wager", "David Grann", Genre.Survival, "292345768801",
                "of shipwreck, survival, and savagery, culminating in a court martial that reveals a shocking truth. The powerful narrative reveals the deeper meaning of the events on The Wager, showing that it was not only the captain and crew who ended up on trial, but the very idea of empire.\n" +
                        "\n" +
                        "On January 28, 1742, a ramshackle vessel of patched-together wood and cloth washed up on the coast of Brazil. Inside were thirty emaciated men, barely alive, and they had an extraordinary tale to tell. They were survivors of His Majesty's Ship the Wager, a British vessel that had left England in 1740 on a secret mission during an imperial war with Spain. While the Wager had been chasing a Spanish treasure-filled galleon known as \"the prize of all the oceans,\" it had wrecked on a desolate island off the coast of Patagonia. The men, after being marooned for months and facing starvation, built the flimsy craft and sailed for more than a hundred days, traversing nearly 3,000 miles of storm-wracked seas. They were greeted as heroes.\n" +
                        "\n" +
                        "But then ... six months later, another, even more decrepit craft landed on the coast of Chile. This boat contained just three castaways, and they told a very different story. The thirty sailors who landed in Brazil were not heroes - they were mutineers. The first group responded with countercharges of their own, of a tyrannical and murderous senior officer and his henchmen. It became clear that while stranded on the island the crew had fallen into anarchy, with warring factions fighting for dominion over the barren wilderness. As accusations of treachery and murder flew, the Admiralty convened a court martial to determine who was telling the truth. The stakes were life-and-death--for whomever the court found guilty could hang.\n" +
                        "\n" +
                        "The Wager is a grand tale of human behavior at the extremes told by one of our greatest nonfiction writers. Grann's recreation of the hidden world on a British warship rivals the work of Patrick O'Brian, his portrayal of the castaways' desperate straits stands up to the classics of survival writing such as The Endurance, and his account of the court martial has the savvy of a Scott Turow thriller. As always with Grann's work, the incredible twists of the narrative hold the reader spellbound."
        ));

        books.add(new Book("Life of Pi", "Yann Martel", Genre.Survival, "292344468801",
                "Life of Pi is a fantasy adventure novel by Yann Martel published in 2001. The protagonist, Piscine Molitor \"Pi\" Patel, a Tamil boy from Pondicherry, explores issues of spirituality and practicality from an early age. He survives 227 days after a shipwreck while stranded on a boat in the Pacific Ocean with a Bengal tiger named Richard Parker.\n"
        ));

        books.add(new Book("The Martian", "Andy Weir", Genre.Survival, "292344464801",
                "Six days ago, astronaut Mark Watney became one of the first people to walk on Mars.\n" +
                "\n" +
                "Now, he’s sure he’ll be the first person to die there.\n" +
                "\n" +
                "After a dust storm nearly kills him and forces his crew to evacuate while thinking him dead, Mark finds himself stranded and completely alone with no way to even signal Earth that he’s alive—and even if he could get word out, his supplies would be gone long before a rescue could arrive.\n" +
                "\n" +
                "Chances are, though, he won’t have time to starve to death. The damaged machinery, unforgiving environment, or plain-old “human error” are much more likely to kill him first.\n" +
                "\n" +
                "But Mark isn’t ready to give up yet. Drawing on his ingenuity, his engineering skills — and a relentless, dogged refusal to quit — he steadfastly confronts one seemingly insurmountable obstacle after the next. Will his resourcefulness be enough to overcome the impossible odds against him?"
        ));

        books.add(new Book("Lord of the Flies", "William Golding", Genre.Survival, "292346664801",
                "At the dawn of the next world war, a plane crashes on an uncharted island, stranding a group of schoolboys. At first, with no adult supervision, their freedom is something to celebrate; this far from civilization the boys can do anything they want. Anything. They attempt to forge their own society, failing, however, in the face of terror, sin and evil. And as order collapses, as strange howls echo in the night, as terror begins its reign, the hope of adventure seems as far from reality as the hope of being rescued. Labeled a parable, an allegory, a myth, a morality tale, a parody, a political treatise, even a vision of the apocalypse, Lord of the Flies is perhaps our most memorable novel about “the end of innocence, the darkness of man’s heart.”"
        ));

        books.add(new Book("The Maze Runner", "James Dasher", Genre.Survival, "292346964801",
                "If you ain’t scared, you ain’t human.\n" +
                        "\n" +
                        "When Thomas wakes up in the lift, the only thing he can remember is his name. He’s surrounded by strangers—boys whose memories are also gone.\n" +
                        "\n" +
                        "Nice to meet ya, shank. Welcome to the Glade.\n" +
                        "\n" +
                        "Outside the towering stone walls that surround the Glade is a limitless, ever-changing maze. It’s the only way out—and no one’s ever made it through alive.\n" +
                        "\n" +
                        "Everything is going to change.\n" +
                        "\n" +
                        "Then a girl arrives. The first girl ever. And the message she delivers is terrifying.\n" +
                        "\n" +
                        "Remember. Survive. Run."
        ));

        books.add(new Book("On the Island", "Tracy Gravis Graves", Genre.Survival, "29234555801",
                "When thirty-year-old English teacher Anna Emerson is offered a job tutoring T.J. Callahan at his family's summer rental in the Maldives, she accepts without hesitation; a working vacation on a tropical island trumps the library any day.\n" +
                        "\n" +
                        "T.J. Callahan has no desire to leave town, not that anyone asked him. He's almost seventeen and if having cancer wasn't bad enough, now he has to spend his first summer in remission with his family—and a stack of overdue assignments—instead of his friends.\n" +
                        "\n" +
                        "Anna and T.J. are en route to join T.J.'s family in the Maldives when the pilot of their seaplane suffers a fatal heart attack and crash-lands in the Indian Ocean. Adrift in shark-infested waters, their life jackets keep them afloat until they make it to the shore of an uninhabited island. Now Anna and T.J. just want to survive and they must work together to obtain water, food, fire, and shelter.\n" +
                        "\n" +
                        "Their basic needs might be met but as the days turn to weeks, and then months, the castaways encounter plenty of other obstacles, including violent tropical storms, the many dangers lurking in the sea, and the possibility that T.J.'s cancer could return. As T.J. celebrates yet another birthday on the island, Anna begins to wonder if the biggest challenge of all might be living with a boy who is gradually becoming a man.\n"
        ));


        //NonFiction
        books.add(new Book("Clean Code", "Robert C. Martin", Genre.NonFiction, "9780132350884",
                "Even bad code can function. But if code isn't clean, it can bring a development organization to its knees. Every year, countless hours and significant resources are lost because of poorly written code. But it doesn't have to be that way.\n" +
                        "Noted software expert Robert C. Martin presents a revolutionary paradigm with Clean Code: A Handbook of Agile Software Craftsmanship . Martin has teamed up with his colleagues from Object Mentor to distill their best agile practice of cleaning code on the fly into a book that will instill within you the values of a software craftsman and make you a better programmer but only if you work at it.\n" +
                        "What kind of work will you be doing? You'll be reading code - lots of code. And you will be challenged to think about what's right about that code, and what's wrong with it. More importantly, you will be challenged to reassess your professional values and your commitment to your craft.\n" +
                        "Clean Code is divided into three parts. The first describes the principles, patterns, and practices of writing clean code. The second part consists of several case studies of increasing complexity. Each case study is an exercise in cleaning up code - of transforming a code base that has some problems into one that is sound and efficient. The third part is the payoff: a single chapter containing a list of heuristics and \"smells\" gathered while creating the case studies. The result is a knowledge base that describes the way we think when we write, read, and clean code.\n" +
                        "Readers will come away from this book understanding\n" +
                        "\n" +
                        "‣ How to tell the difference between good and bad code\n" +
                        "‣ How to write good code and how to transform bad code into good code\n" +
                        "‣ How to create good names, good functions, good objects, and good classes\n" +
                        "‣ How to format code for maximum readability\n" +
                        "‣ How to implement complete error handling without obscuring code logic\n" +
                        "‣ How to unit test and practice test-driven development\n" +
                        "\n" +
                        "This book is a must for any developer, software engineer, project manager, team lead, or systems analyst with an interest in producing better code."
        ));

        books.add(new Book("Atomic Habits", "James Clear", Genre.NonFiction, "5780100220888",
                "No matter your goals, Atomic Habits offers a proven framework for improving—every day. James Clear, one of the world's leading experts on habit formation, reveals practical strategies that will teach you exactly how to form good habits, break bad ones, and master the tiny behaviors that lead to remarkable results.\n" +
                        "\n" +
                        "If you're having trouble changing your habits, the problem isn't you. The problem is your system. Bad habits repeat themselves again and again not because you don't want to change, but because you have the wrong system for change. You do not rise to the level of your goals. You fall to the level of your systems. Here, you'll get a proven system that can take you to new heights.\n" +
                        "\n" +
                        "Clear is known for his ability to distill complex topics into simple behaviors that can be easily applied to daily life and work. Here, he draws on the most proven ideas from biology, psychology, and neuroscience to create an easy-to-understand guide for making good habits inevitable and bad habits impossible. Along the way, readers will be inspired and entertained with true stories from Olympic gold medalists, award-winning artists, business leaders, life-saving physicians, and star comedians who have used the science of small habits to master their craft and vault to the top of their field.\n" +
                        "\n" +
                        "Learn how to:\n" +
                        "- Make time for new habits (even when life gets crazy);\n" +
                        "- Overcome a lack of motivation and willpower;\n" +
                        "- Design your environment to make success easier;\n" +
                        "- Get back on track when you fall off course;\n" +
                        "...and much more.\n" +
                        "\n" +
                        "Atomic Habits will reshape the way you think about progress and success, and give you the tools and strategies you need to transform your habits--whether you are a team looking to win a championship, an organization hoping to redefine an industry, or simply an individual who wishes to quit smoking, lose weight, reduce stress, or achieve any other goal."
        ));

        books.add(new Book("The Art of War", "Sun Tzu", Genre.NonFiction, "578099920888",
                "Twenty-Five Hundred years ago, Sun Tzu wrote this classic book of military strategy based on Chinese warfare and military thought. Since that time, all levels of military have used the teaching on Sun Tzu to warfare and civilization have adapted these teachings for use in politics, business and everyday life. The Art of War is a book which should be used to gain advantage of opponents in the boardroom and battlefield alike."
        ));

        books.add(new Book("Introduction to Algorithms", "Thomas H. Cormen", Genre.NonFiction, "9780132350884",
                "A comprehensive update of the leading algorithms text, with new material on matchings in bipartite graphs, online algorithms, machine learning, and other topics.\n" +
                        "\n" +
                        "Some books on algorithms are rigorous but incomplete; others cover masses of material but lack rigor. Introduction to Algorithms uniquely combines rigor and comprehensiveness. It covers a broad range of algorithms in depth, yet makes their design and analysis accessible to all levels of readers, with self-contained chapters and algorithms in pseudocode. Since the publication of the first edition, Introduction to Algorithms has become the leading algorithms text in universities worldwide as well as the standard reference for professionals. This fourth edition has been updated throughout.\n" +
                        "\n" +
                        "New for the fourth edition\n" +
                        "New chapters on matchings in bipartite graphs, online algorithms, and machine learningNew material on topics including solving recurrence equations, hash tables, potential functions, and suffix arrays140 new exercises and 22 new problemsReader feedback-informed improvements to old problemsClearer, more personal, and gender-neutral writing styleColor added to improve visual presentationNotes, bibliography, and index updated to reflect developments in the fieldWebsite with new supplementary material\n" +
                        "Warning: Avoid counterfeit copies of Introduction to Algorithms by buying only from reputable retailers. Counterfeit and pirated copies are incomplete and contain errors."
        ));

        books.add(new Book("The C Programming Language", "Brian W. Kernighan", Genre.NonFiction, "9780132650884",
                "This book is meant to help the reader learn how to program in C. It is the definitive reference guide, now in a second edition. Although the first edition was written in 1978, it continues to be a worldwide best-seller. This second edition brings the classic original up to date to include the ANSI standard.\n" +
                        "\n" +
                        "From the Preface:\n" +
                        "We have tried to retain the brevity of the first edition. C is not a big language, and it is not well served by a big book. We have improved the exposition of critical features, such as pointers, that are central to C programming. We have refined the original examples, and have added new examples in several chapters. For instance, the treatment of complicated declarations is augmented by programs that convert declarations into words and vice versa. As before, all examples have been tested directly from the text, which is in machine-readable form.\n" +
                        "As we said in the first preface to the first edition, C \"wears well as one's experience with it grows.\" With a decade more experience, we still feel that way. We hope that this book will help you to learn C and use it well."
        ));

        books.add(new Book("Operating Systems: Three Easy Pieces", "Remzi H. Arpaci-Dusseau", Genre.NonFiction, "9780100650884",
                "A book about modern operating systems. Topics are broken down into three major conceptual pieces: Virtualization, Concurrency, and Persistence. Includes all major components of modern systems including scheduling, virtual memory management, disk subsystems and I/O, file systems, and even a short introduction to distributed systems."
        ));
    }

    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    @Override
    public Book getBookByISBN(String ISBN) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getISBN().equals(ISBN)) {
                return books.get(i);
            }
        }
        return null;
    }

    @Override
    public Book getBookByTitle(String title) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getName().equalsIgnoreCase(title)) {
                return books.get(i);
            }
        }
        return null;
    }

    @Override
    public Book addBook(Book newBook) {
        Book result = null;

        //check duplicate ISBN and title
        if(!isDuplicateISBN(newBook.getISBN()) && !isDuplicateTitle(newBook.getName())) {
            books.add(newBook);
            result = newBook;
        }

        return result;
    }

    private boolean isDuplicateISBN(String ISBN) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getISBN().equals(ISBN)) {
                return true;
            }
        }
        return false;
    }

    private boolean isDuplicateTitle(String title) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getName().equalsIgnoreCase(title)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void removeBookByISBN(String ISBN) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getISBN().equals(ISBN)) {
                books.remove(i);
            }
        }
    }

    @Override
    public void removeBookByTitle(String Title) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getName().equalsIgnoreCase(Title)) {
                books.remove(i);
            }
        }
    }

    //Create list of books by author by traversing through booklist
    //Currently implemented as String. Need to change to Author author after changes are made to Object
    @Override
    public List<Book> getBooksByAuthor(String author) {
        List<Book> booksByAuthor = new ArrayList<>();

        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getAuthor().equalsIgnoreCase(author)) {
                booksByAuthor.add(books.get(i));
            }
        }

        if (booksByAuthor.isEmpty()) {
            return null;
        }

        return booksByAuthor;
    }

    @Override
    public List<Book> getBooksByGenre(Genre genre) {
        List<Book> booksByGenre = new ArrayList<>();

        for (Book book : books) {
            for (Genre bookGenre : book.getGenre()) {
                if (genre == bookGenre) {
                    booksByGenre.add(book);
                }
            }
        }

        if (booksByGenre.isEmpty()) {
            return null;
        }

        return booksByGenre;
    }


}
