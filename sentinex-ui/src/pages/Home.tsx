import { useState } from 'react'
import TopicCard from '../components/TopicCard'
import NewsList from '../components/NewsList'
import { fetchNewsSentiment } from '../services/api'

const topics = ["AI", "Banking", "Energy", "Tech", "Crypto"]

export default function Home() {
    const [articles, setArticles] = useState([])

    const handleTopicClick = async (topic: string) => {
        const data = await fetchNewsSentiment(topic)
        setArticles(data)
    }

    return (
        <div className="p-6 max-w-4xl mx-auto">
            <h1 className="text-3xl font-bold mb-4">Sentinex Topic Explorer</h1>
            <div className="flex flex-wrap">
                {topics.map((topic) => (
                    <TopicCard key={topic} topic={topic} onClick={handleTopicClick} />
                ))}
            </div>
            <NewsList articles={articles} />
        </div>
    )
}
