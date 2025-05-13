package com.spring.blogsport.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.blogsport.model.Post;
import com.spring.blogsport.repository.BlogsportRepository;

//import jakarta.annotation.PostConstruct;

@Component
public class DummyData {

    @Autowired
    BlogsportRepository blogsportRepository;

    //@PostConstruct
    public void savePosts() {
        List<Post> postList = new ArrayList<>();

        Post post1 = new Post();
        post1.setAuthor("Fabrizio Romano");
        post1.setDate(LocalDate.now());
        post1.setTitle(
                "Lamine Yamal: “Maybe the other teams are scared of Real Madrid… 𝐰𝐞 𝐝𝐨𝐧’𝐭 𝐟𝐞𝐚𝐫 𝐭𝐡𝐞𝐦” 👀");
        post1.setText(
                "Lamine Yamal: “Maybe the other teams are scared of Real Madrid… 𝐰𝐞 𝐝𝐨𝐧’𝐭 𝐟𝐞𝐚𝐫 𝐭𝐡𝐞𝐦” 👀\n"
                        + //
                        "\n" + //
                        "“We feel 𝐬𝐮𝐩𝐞𝐫𝐢𝐨𝐫 to every other team, that’s the view”.\n" + //
                        "\n" + //
                        "“At the end of the day, when Real Madrid beats you, it doesn’t sit well... during the recent years we suffered, and this years we gave our all, our 𝐛𝐞𝐬𝐭 𝐦𝐞𝐧𝐭𝐚𝐥𝐢𝐭𝐲” 🧠");

        Post post2 = new Post();
        post2.setAuthor("Fabrizio Romano");
        post2.setDate(LocalDate.now());
        post2.setTitle("𝐔𝐏𝐃𝐀𝐓𝐄: Real Madrid consider Dean Huijsen for centre back position");
        post2.setText(
                "Xabi Alonso wants new centre back soon with Saliba, Konaté and Huijsen all included in Real Madrid’s list.\n"
                        + //
                        "\n" + //
                        "Saliba, dream target for present/future but currently considered too expensive as Arsenal offer new deal.\n"
                        + //
                        "\n" + //
                        "Konaté is out of contract in June 2026 at Liverpool… negotiations are not advancing, Real are interested.\n"
                        + //
                        "\n" + //
                        "❗️ Huijsen can be available easily for £50m release clause, he’s Spanish and keen on the move giving his 𝐩𝐫𝐢𝐨𝐫𝐢𝐭𝐲 to Real Madrid in case they want to advance.\n"
                        + //
                        "\n" + //
                        "Chelsea, Liverpool and Arsenal all want to pay release clause for Huijsen this month, still insisting to make it happen… Real Madrid are expected to decide soon.");

        /*
        postList.add(post1);
        postList.add(post2);

        for (Post post : postList) {
            Post postSaved = blogsportRepository.save(post);
            System.out.println("Post salvo: " + postSaved.getId());
        }
        */
    }
}