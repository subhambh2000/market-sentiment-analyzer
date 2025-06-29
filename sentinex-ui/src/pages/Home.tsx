import {useState} from 'react'
import TopicCard from '../components/TopicCard'
import NewsList from '../components/NewsList'
import {fetchNewsSentiment} from '../services/api'
// import HomePage from '../assets/HomePage.svg'
import HomePageGraph from '../services/HomePageGraph'

const topics = ["AI", "Banking", "Energy", "Tech", "Crypto"]

export default function Home() {
    const [articles, setArticles] = useState([])

    const handleTopicClick = async (topic: string) => {
        const data = await fetchNewsSentiment(topic)
        setArticles(data)
    }

    return (
        <div className="min-h-screen bg-white">
            <div className="relative w-full overflow-hidden">
                <div className="absolute top-[-100px] left-[-100px] w-[300px] h-[300px] bg-indigo-200 rounded-full blur-3xl opacity-30 z-0 inset-0 pointer-events-none opacity-20">
                    <HomePageGraph/>
                </div>
                <div className="flex items-center justify-center min-h-[400px]">
                </div>
            </div>
            <div className="relative z-10 max-w-4xl mx-auto px-4">
                <div className="flex items-center mb-4 text-slate-800 font-semibold">
                    <span className="mr-2">Topics:</span>
                    <div className="flex flex-wrap gap-2 font-normal" style={{display: "inline"}}>
                        {topics.map((topic) => (
                            <TopicCard key={topic} topic={topic} onClick={handleTopicClick}/>
                        ))}
                    </div>
                </div>
                <NewsList articles={articles}/>
            </div>
        </div>
    )
}
