package honda.bookworm.tests.Business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import honda.bookworm.Business.Managers.AccessBooks;
import honda.bookworm.Data.IBookPersistence;
import honda.bookworm.Data.hsqldb.BookPersistenceHSQLDB;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;
import honda.bookworm.tests.utils.TestUtils;

public class AccessBooksIT {
    private AccessBooks accessBooks;
    private File tempDB;

    @Before
    public void setup() throws IOException {
        this.tempDB = TestUtils.copyDB();
        final IBookPersistence persistence = new BookPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        this.accessBooks = new AccessBooks(persistence);
    }

    @Test
    public void testGetBooksGenreFound(){
        System.out.println("\nStarting testGetBooksGenreFound");
        accessBooks.addBook(new Book("The Way of Kings", "Brandon Sanderson", Genre.Fantasy, "9780765326355",
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

        accessBooks.addBook(new Book("Mistborn", "Brandon Sanderson", Genre.Fantasy, "9780765350381",
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

        List<Book> books = accessBooks.getBooksGenre(Genre.Fantasy);
        assertEquals(2, books.size());

        System.out.println("\nFinished testGetBooksGenreFound");
    }

    @Test
    public void testAddBook(){
        System.out.println("\nStarting testAddBook");

        Book book = new Book("Sword of Destiny", "Andrzej Sapkowski", Genre.Adult, "970575077832",
                "Geralt is a witcher, a man whose magic powers, enhanced by long training and a mysterious elixir, have made him a brilliant fighter and a merciless assassin. Yet he is no ordinary murderer: his targets are the multifarious monsters and vile fiends that ravage the land and attack the innocent.\n" +
                        "\n" +
                        "This is a collection of short stories, following the adventures of the hit collection THE LAST WISH. Join Geralt as he battles monsters, demons and prejudices alike..."
        );
        assertNotNull(book);
        Book addedBook = accessBooks.addBook(book);
        assertEquals(book, addedBook);

        System.out.println("\nFinished testAddBook");
    }

    @After
    public void tearDown(){
        this.tempDB.delete();
    }

}
